
[![Build Status](https://travis-ci.org/Arquisoft/Loader_e5a.svg?branch=master)](https://travis-ci.org/Arquisoft/Loader_e5a)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/81ed23f28056410c9d542489fba9b901)](https://www.codacy.com/app/jelabra/Loader_e5a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Loader_e5a&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/Loader_e5a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Loader_e5a)

# Loader_e5a

Loader module

# New Authors

Alejandro Barrera Sánchez (UO251893)

Tania Álvarez Díaz (UO244856)

Daniel Bermejo Blanco (UO204115)

Ismael Cadenas Alonso (UO251025)

# Authors

Daniel Alba Muñiz (UO245188)

José Luis Bugallo González (UO244702)

Ignacio Escribano Burgos (UO227766)

Daniel Duque Barrientos (UO245553)

Rubén de la Varga Cabero (UO246977)



# Compilar

Ejecutar maven en la carpeta del proyecto, con la opción compile.

Si está en las variables de entorno de windows:

  - mvn compile
   - mvn package
  
Si no:

  - /"rutamavem"/bin/mvn.cmd compile
  - /"rutamaven"/bin/mvn.cmd package
  
# Probar

Si está en las variables de entorno de windows:

  - mvn test
  
Si no:

  - "rutamavem"/bin/mvn.cmd test
  
# Ejecutar

Mejor incluir ruta completa hasta los archivos

- java -jar "jar-generado" "ruta del excel de carga"
