# job4j_tracker
## Учебный проект "Трекер".
### Представляет из себя консольное приложение для работы с заявками,
### поэтапно дорабатываемое по мере прохождения курса.
---
схема зависимости классов.

![Alt](https://github.com/Daniil62/job4j_tracker/blob/master/tracker_dependency_diagram.png)

- Класс StartUI отвечает за пользовательский интерфейс,
- Класс Tracker за логику операций с заявками,
- Класс Item - модель данных описывающая заявку,
- Класс Input отвечает за входящие от пользователя данные.
---
### Функционал приложения это:
- добавление заявки
- редактирование заявки
- поиск заявки по имени
- поиск заявки по id
- удаление заявки
- вывод всех заявок на консоль.
---
### За каждое действие отвечает отдельный класс реализующий интерфейс UserAction.
### В проекте использован шаблон "Стратегия".
---
### Входящие данные проходят валидацию, реализованную с помощью шаблона "Декоратор".
