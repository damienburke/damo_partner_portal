package com.ssd.gateway

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.web.server.LocalServerPort
import org.testcontainers.containers.DockerComposeContainer
import java.io.File
import java.net.http.HttpClient
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

abstract class BaseIT {

    @LocalServerPort
    var port: Int = 0

    companion object {

        private lateinit var instance: KDockerComposeContainer

        class KDockerComposeContainer(file: File) : DockerComposeContainer<KDockerComposeContainer>(file)

        private fun defineDockerCompose(): KDockerComposeContainer {
            val userDir = System.getProperty("user.dir")
            println("Project root directory: $userDir")
            val localFolder: Path = Paths.get(userDir, "local")
            val dockerComposeFile: Path =
                localFolder.resolve("docker-compose.base.yaml")

            if (Files.notExists(dockerComposeFile)) {
                throw IllegalStateException("docker-compose.yaml not found at: ${dockerComposeFile.toAbsolutePath()}")
            }

            return KDockerComposeContainer(dockerComposeFile.toFile())
                .withExposedService("redis_1", 6379)
        }

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            instance = defineDockerCompose()
            println("starting redis..")
            instance.start()
            println("redis started")
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            instance.stop()
        }
    }

    val httpClient = HttpClient.newHttpClient()
}

const val baseUrl = "http://localhost"


