package com.checkify

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import freemarker.cache.*
import io.ktor.freemarker.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import org.slf4j.Logger
import sun.jvm.hotspot.HelloWorld
import org.slf4j.LoggerFactory

private val logger : Logger = LoggerFactory.getLogger(Application::class.java);

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/setup") {
            call.respondText("SETUP", contentType = ContentType.Text.Plain)
        }

        post("/event") {
            call.respondText("EVENT Received", contentType = ContentType.Text.Plain)
            logger.info(call.receiveText())
        }

        post("/accesstoken") {
            call.respond(FreeMarkerContent("accesstoken.ftl", mapOf("accessToken" to "qwe")))
            val body = call.receiveText()
            logger.info(body)
        }
    }
}