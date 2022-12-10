package org.Lazarev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi();
        try{
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        Model model=new Model();
        Message message=update.getMessage();
        if(message!=null && message.hasText()){
            switch (message.getText()){

                case "/start":
                    sendMsg(message,"Привет, подскажу тебе погоду в любом городе мира! Напиши город");
                    break;
                default:
                    try{
                        sendMsg(message,Weather.getWeather(message.getText(),model));
                    }catch (IOException e){
                        sendMsg(message,"Такой город не найден, попробуй снова!");
                    }
            }
        }
    }
    private void sendMsg(Message message,String text){
        SendMessage sendMessage=new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            sendMessage(sendMessage);
        }catch(TelegramApiException e){
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return "MyWeatherForecastBot";
    }

    @Override
    public String getBotToken() {
        return "5822281707:AAFnX6rCbtUtupFhXztDCRuik7GIps8uVH8";
    }
}
