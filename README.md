This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

### Tester sur IOS
Pour pouvoir tester l'app sur ios il faut disposer d'un mac et installer ces outils:
- Installer [Homebrew](https://brew.sh/fr)
  ```shell
  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
  ```
  Copiez et collez dans une fenetre du terminal.
  
  Le script explique ce qu'il av faire, puis fait une pause,
  avant de l'executer.
  
  Generalement il y a plus rien a faire mais si apres l'installation Vous avez l'information :
  ====> Next steps - qui vous donnera les instructions de ce qu'il faut faire apres (Ajout au path et autre...)
  Apres brew sera operationnel

-  Installer [JDK17](https://www.oracle.com/java/technologies/downloads/)
  ```shell
  brew intstall openjdk@17
  ```
  Apres l'installation, il y a une information concernant l'ajout au PATH
  Au cas ou, cet information est absent, verifiez si elle a ete automatiquement ajouter:
  
  sinon il faut faire:
  ```shell
  echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:PATH"' >> ~./zshrc
  ```
  Apres:
  ```shell
  export CPPFLAGS="-I/opt/homebrew/opt/openjdk@17/include"
  ```
  Testez:
  ```shell
  java --version
  javac --version
  ```
- Installer [Xcode](https://developper.apple.com/fr/support/xcode/)
  ```shell
  xcode-select --install
  ```

- Installer CocoaPods
 ```shell
  brew install cocoapods
  ```
  Verfiez l'installation
  ```shell
  pod --version
  ```
  
- Installer android studio
  ```shell
  brew install --cask android-studio
  ```
  Ouvrez android studio, a gauche selectionnez plugin et installez "Kotlin Multiplatform"
