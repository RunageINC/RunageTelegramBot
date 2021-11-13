package com.runage.ia.telegrambot.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TelegramActionControl {

	private TelegramBotCore core;
	private GetUpdatesResponse updatesResponse;
	private SendResponse sendResponse;
	private BaseResponse baseResponse;
	private int offset;
	private static int messageLimit = 100;
	
	public TelegramActionControl(@Value("api.token") String apiToken) {
		core = new TelegramBotCore(apiToken);
	}
	
	public void run() {
		while(true) {
			updatesResponse = core.executeMessageGathering(messageLimit);
			
			for (Update update : getMessageList(updatesResponse)) {
				core.addToOffset(update.updateId() + 1);
				
				baseResponse = core.executeResponse(update);
				
				log.info("Response send stats ok: {[]}", baseResponse.isOk());
				
				sendResponse = core.sendMessage(update, "Working with refactor!");
				
				log.info("Message send stats ok: {[]}", sendResponse.isOk());
			}
		}
	}
	
	public List<Update> getMessageList(GetUpdatesResponse updatesResponse) {
		return updatesResponse.updates();
	}
}
