package io.kotest.provided

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension

object ProjectConfig : AbstractProjectConfig() {
    override val isolationMode = IsolationMode.InstancePerTest

    override fun extensions() = listOf<Extension>(MongoListener, MicronautKotest5Extension)
}
