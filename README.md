# Тестовое задание

Android-приложение для просмотра списка городов и информации о выбранном городе.

## О проекте

Реализовано два экрана:

1. Экран списка городов
2. Экран информации о городе

Пользователь может:
- просматривать список городов, полученных с API
- искать города по названию
- смотреть детали по выбранному городу
- переходить в браузер для поиска дополнительной информации о городе

## Стек технологий

В проекте использованы:

- Kotlin
- Jetpack Compose
- Orbit MVI
- Ktor
- Hilt DI
- Coroutines
- REST API
- JSON serialization
- Navigation Compose

## Архитектурный подход

Проект организован по слоям:

- `data` — работа с API, модели ответа, мапперы, repository, service
- `domain` — бизнес-модели, repository contracts, use case слой
- `presentation` — ViewModel, Orbit state/side effects
- `ui` — composable-экраны и route-слой
- `di` — dependency injection модули
- `base` — общие абстракции и утилиты
