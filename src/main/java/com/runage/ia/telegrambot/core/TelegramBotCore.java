package com.runage.ia.telegrambot.core;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramBotCore {

	private TelegramBot bot;
	
	int messageOffset;
	
	public TelegramBotCore(TelegramBot bot,
						   String accessToken) {
		this.messageOffset = 0;
		this.bot = new TelegramBot(accessToken);
	}
	
	public TelegramBotCore(String accessToken) {
		this.bot = new TelegramBot(accessToken);
	}
	
	public void addToOffset(int value) {
		this.messageOffset = value;
	}
	
	public GetUpdatesResponse executeMessageGathering(int messageLimit) {
		return this.bot.execute(new GetUpdates().limit(messageLimit).offset(messageOffset));
	}
	
	public BaseResponse executeResponse(Update update) {
		return this.bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}
	
	public SendResponse sendMessage(Update update, String message) {
		return this.bot.execute(new SendMessage(update.message().chat().id(), message));
	}
}
