# Rizo_Post1_U7
# rizo-post1-u7 – NotifyMe (Unidad 7)

## Descripción

Este proyecto corresponde al laboratorio de la Unidad 7, en el cual se implementa el manejo de permisos en tiempo de ejecución, la configuración de canales de notificación y la integración de notificaciones push mediante Firebase Cloud Messaging (FCM).

La aplicación está diseñada para gestionar correctamente el permiso de notificaciones, garantizando que la funcionalidad no falle en caso de que el usuario lo rechace, y ofreciendo una experiencia de usuario adecuada.

---

## Objetivo

Desarrollar una aplicación Android que permita:

* Solicitar el permiso de notificaciones usando Activity Result API
* Configurar canales de notificación compatibles con Android 8 o superior
* Recibir notificaciones push desde Firebase
* Manejar correctamente los casos en los que el permiso es denegado

---

## Tecnologías utilizadas

* Kotlin
* Android Studio
* Firebase Cloud Messaging
* Activity Result API
* Notification Channels
* WorkManager

---

## Permisos utilizados

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.INTERNET"/>
```

---

## Canales de notificación

Se implementaron tres canales de notificación:

* channel_general: utilizado para avisos generales
* channel_urgent: utilizado para alertas importantes
* channel_silent: utilizado para procesos en segundo plano sin interrupciones

Cada canal fue configurado con su respectiva importancia, nombre y descripción.

---

## Configuración de Firebase

Para el funcionamiento de las notificaciones push se realizó la siguiente configuración:

1. Creación del proyecto en Firebase Console
2. Registro de la aplicación Android con su package name
3. Descarga del archivo google-services.json
4. Ubicación del archivo en la carpeta app/ del proyecto
5. Configuración de dependencias en Gradle

### Dependencias principales

```kotlin
plugins {
    id("com.google.gms.google-services")
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-messaging-ktx")
}
```

---

## Ejecución del proyecto

Para ejecutar el proyecto se deben seguir los siguientes pasos:

1. Clonar el repositorio
2. Abrir el proyecto en Android Studio
3. Sincronizar Gradle
4. Ejecutar la aplicación en un dispositivo o emulador

---

## Resultados y pruebas

### Checkpoint 1: Permisos y canales

* El permiso POST_NOTIFICATIONS fue declarado correctamente
* Se implementó el diálogo explicativo cuando el permiso es denegado
* Los canales de notificación son visibles en la configuración del dispositivo
* Cada canal presenta su configuración adecuada

Evidencias en: capturas/checkpoint1

---

### Checkpoint 2: Notificaciones FCM

* El token FCM se muestra en Logcat con la etiqueta "FCM"
* Se logró recibir notificaciones desde Firebase Console
* Las notificaciones con tipo "urgent" se asignan correctamente al canal de alta prioridad
* Al interactuar con la notificación se abre la aplicación

Evidencias en: capturas/checkpoint2

---

### Checkpoint 3: Manejo de permiso denegado

* La aplicación continúa funcionando aun cuando el permiso es rechazado
* Se muestra un mensaje explicativo al volver a solicitar el permiso
* El usuario puede cancelar sin que se genere ningún error

Evidencias en: capturas/checkpoint3

---

## Estructura del proyecto

app/
├── src/main/
│   ├── java/
│   │   ├── PermissionManager.kt
│   │   ├── NotificationChannelManager.kt
│   │   ├── NotifyMeMessagingService.kt
│   │   ├── NotifyMeApp.kt
│   │   └── MainActivity.kt
│   ├── AndroidManifest.xml
│   └── res/
capturas/
README.md

---

## Buenas prácticas aplicadas

* Uso de APIs modernas sin métodos deprecados
* Separación de responsabilidades en diferentes clases
* Manejo adecuado del flujo de permisos
* Organización clara del código

---

## Commits realizados

* Agrega permisos de notificación
* Implementa canales de notificación
* Integra Firebase Cloud Messaging

---

## Conclusión

El desarrollo de este laboratorio permitió comprender la importancia del manejo adecuado de permisos en Android, así como la correcta configuración de notificaciones y su integración con servicios externos como Firebase. Además, se garantizó que la aplicación mantuviera estabilidad y una experiencia de usuario adecuada en diferentes escenarios.

---

## Repositorio

https://github.com/02230132004/Miguel-Rizo-previo-p2
