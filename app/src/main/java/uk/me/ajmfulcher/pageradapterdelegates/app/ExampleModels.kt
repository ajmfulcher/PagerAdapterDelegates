package uk.me.ajmfulcher.pageradapterdelegates.app

interface ExampleModel

class StartItem: ExampleModel

class WithStringPayloadItem(private val payload: String): ExampleModel {

    fun getPayload(): String = payload

}

class EndItem: ExampleModel
