## Телеграм бот для общения с chat gpt
### команды
/start приветствие 

/clear очищаем историю сообщения с gpt для пользователя

## запуск приложения
````
docker build -t gpt-tg-bot
docker run -d -p 8080:8080 gpt-tg-bot
````