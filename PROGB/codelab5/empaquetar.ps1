$ErrorActionPreference = 'Stop'

$proyecto = $PSScriptRoot
$controlador = Join-Path $proyecto 'lib\sqlite-jdbc-3.54.1.0.jar'
$lanzador = Join-Path $proyecto 'iniciar-juego.bat'
$ranking = Join-Path $proyecto 'ranking.db'
$destino = Join-Path $proyecto 'LeyendasDelCodigo.zip'

if (Test-Path -LiteralPath $destino) {
    throw 'LeyendasDelCodigo.zip ya existe. Cambia su nombre o muevelo antes de empaquetar.'
}
if (-not (Test-Path -LiteralPath $controlador -PathType Leaf)) {
    throw 'Falta lib\sqlite-jdbc-3.54.1.0.jar.'
}
if (-not (Test-Path -LiteralPath $lanzador -PathType Leaf)) {
    throw 'Falta iniciar-juego.bat.'
}

# El directorio temporal es exclusivo de esta ejecucion.
$temporal = Join-Path ([System.IO.Path]::GetTempPath()) ('leyendas-' + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path $temporal -ErrorAction Stop | Out-Null
try {
    $clases = Join-Path $temporal 'clases'
    $paquete = Join-Path $temporal 'paquete'
    $lib = Join-Path $paquete 'lib'
    New-Item -ItemType Directory -Path $clases, $paquete, $lib | Out-Null

    $archivos = @(Get-ChildItem -LiteralPath (Join-Path $proyecto 'src') -Recurse -Filter '*.java' |
        ForEach-Object { $_.FullName })
    if ($archivos.Count -eq 0) {
        throw 'No se han encontrado archivos Java en src.'
    }

    & javac -encoding UTF-8 -d $clases $archivos
    if ($LASTEXITCODE -ne 0) {
        throw 'Ha fallado la compilacion.'
    }

    $jar = Join-Path $paquete 'LeyendasDelCodigo.jar'
    & jar cfe $jar Main -C $clases .
    if ($LASTEXITCODE -ne 0) {
        throw 'No se ha podido crear el JAR.'
    }

    Copy-Item -LiteralPath $controlador -Destination $lib
    Copy-Item -LiteralPath $lanzador -Destination $paquete
    if (Test-Path -LiteralPath $ranking -PathType Leaf) {
        Copy-Item -LiteralPath $ranking -Destination $paquete
        Write-Host 'Se incluira una copia de ranking.db.'
    } else {
        Write-Host 'No existe ranking.db: el juego creara uno vacio al iniciarse.'
    }

    $zipTemporal = Join-Path $temporal 'LeyendasDelCodigo.zip'
    $contenido = @(Get-ChildItem -LiteralPath $paquete -Force | ForEach-Object { $_.FullName })
    Compress-Archive -LiteralPath $contenido -DestinationPath $zipTemporal
    Move-Item -LiteralPath $zipTemporal -Destination $destino -ErrorAction Stop
    Write-Host "Paquete creado: $destino"
} finally {
    Remove-Item -LiteralPath $temporal -Recurse -Force
}
