package uk.me.ajmfulcher.pageradapterdelegates.app

interface ExampleModel {

    fun getName(): CharSequence? = null

}

class StartItem: ExampleModel {

    override fun getName() = "Start"

}

class WithStringPayloadItem(private val payload: String): ExampleModel {

    fun getPayload(): String = payload

}

class EndItem: ExampleModel {

    override fun getName() = "End"

}
