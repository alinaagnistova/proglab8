# proglab8
Лабораторная работа 8 по программированию.<br>

1. Интерфейс должен быть реализован с помощью библиотеки Swing
2. Графический интерфейс клиентской части должен поддерживать <b>русский, немецкий, шведский и английский (Австралия) языки / локали.</b> Должно обеспечиваться корректное отображение чисел, даты и времени в соответстии с локалью. Переключение языков должно происходить без перезапуска приложения. Локализованные ресурсы должны храниться в классе. <br>

Доработать программу из лабораторной работы №7 следующим образом:
Заменить консольный клиент на клиент с графическим интерфейсом пользователя(GUI). 

<b>В функционал клиента должно входить:</b>

1. Окно с авторизацией/регистрацией.
2. Отображение текущего пользователя.
3. Таблица, отображающая все объекты из коллекции<br>
    3.1. Каждое поле объекта - отдельная колонка таблицы.<br>
    3.2. Строки таблицы можно фильтровать/сортировать по значениям любой из колонок. Сортировку и фильтрацию значений столбцов реализовать с помощью Streams API.
4. Поддержка всех команд из предыдущих лабораторных работ.
5. Область, визуализирующую объекты коллекции<br>
    5.1 Объекты должны быть нарисованы с помощью графических примитивов с использованием Graphics, Canvas или аналогичных средств графической библиотеки.<br>
    5.2 При визуализации использовать данные о координатах и размерах объекта.<br>
    5.3 Объекты от разных пользователей должны быть нарисованы разными цветами.<br>
    5.4 При нажатии на объект должна выводиться информация об этом объекте.<br>
    5.5 При добавлении/удалении/изменении объекта, он должен <b>автоматически</b> появиться/исчезнуть/измениться  на области как владельца, так и всех других клиентов.<br> 
    5.6 При отрисовке объекта должна воспроизводиться согласованная с преподавателем <b>анимация.</b>
6. Возможность редактирования отдельных полей любого из объектов (принадлежащего пользователю). Переход к редактированию объекта возможен из таблицы с общим списком объектов и из области с визуализацией объекта.
7. Возможность удаления выбранного объекта (даже если команды remove ранее не было).

