pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.toString() == "io.github.byhook.prefab") {
                useModule("com.github.byhook:prefab-plugin:${requested.version}")
            }
        }
    }
    repositories {
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "media-framework"
include("app")
include("logger")
include("media-camera")
include("media-common")
include("media-decode")
include("media-encode")
include("media-record")

