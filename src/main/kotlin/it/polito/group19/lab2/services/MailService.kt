package it.polito.group19.lab2.services

interface MailService {

    fun sendMessage(toMail: String, subject: String, mailBody: String)

}