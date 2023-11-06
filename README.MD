Comandos básicos de github:
git add . -> Empaqueta todos los cambios para subirlos
git commit -m "Mensaje" -> Cierra el paquete y un mesaje de la version
git pull -> Baja todos los cambios del github
git push -> Sube todos los archivos al git de la rama en la que estes
git status -> Enseña los archivos modificados y que se deben de subirlos
git branch -> Muentra el listado de ramas
git branch <Nombre de rama> -> Crea una rama con ese nombre
git checkout <Nombre de rama> -> Cambia a la rama seleccionada
git branch -d <Nombre de la rama> -> Elimina la rama seleccionada

VER LAS RAMAS DE LOS OTROS:
    1-GENERAR UNA RAMA LOCAL CON EL git branch <Nombre de rama>
    2-Cambiarse a esa rama con el git checkout <Nombre de la rama>
    3-Link de esa rama a la rama local con git branch --set-upstream-to=origin/<Nombre de la rama Online> <Nombre de la rama local>