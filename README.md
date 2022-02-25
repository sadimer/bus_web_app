# Система информации об автобусных рейсах и билетах
Доступна здесь:<место под url>
# Схема базы данных приложения:
![database](database.png?raw=true "database")

# Страницы приложения:
**Стартовая страница:**

Доступна без авторизации, отображается при первоначальном попадании на сайт. Элементы:
+ Поле для поиска билетов из точки А в точку Б в конкретную дату/время. При вводе данных открывается страница с рейсами (с заданным фильтром).
+ Поле входа в аккаунт/регистрации. Открывается страница входа/регистрации.

**Страница с рейсами**

Доступна после введения фильтров поиска на стартовой странице. Элементы:
+ Список рейсов (с указанием свободных мест, точным временем отправления и прибытия, цен за место, номером маршрута)
+ Поле выбора рейса и места, кнопка "Забронировать место". Если пользователь не вошел, при нажатии кнопки сначала открывается страница входа/регистрации, а затем страница информации о заказе. Если авторизация была проведена, то сразу страница информации о заказе.
+ Кнопка информация о рейсе. При нажатии открывается страница информации о рейсе.
+ Кнопка входа в аккаунт/регистрации.
+ Кнопка домой (возврат на стартовую страницу).

**Страница информации о рейсе**

Здесь расположены:
+ Список названий всех станций данного маршрута, времена прибытия и отправления, компания перевозчик.
+ Кнопка входа в аккаунт/регистрации.
+ Кнопка назад и домой (возврат на страницу с рейсами и на стартовую соответственно).

**Страница информации о заказе**

Не доступна без авторизации!
Здесь отображается:
+ Номер рейса, время отправления и прибытия, место, номер билета, конечная и стартовая станции.
+ Кнопка подтверждения брони (делает запись с user id в таблицу tickets и возвращает на стартовую страницу).

**Страница входа/регистрации**

Здесь расположены:
+ Поле ввода логина, пароля (в случае выбора регистрации еще ФИО, контактные данные). После успешного входа или регистрации открывается предыдущая страница. Рядом с кнопкой домой появляется кнопки "Мои заказы" и "Мои данные" (в случае если пользователь admin, то еще кнопка "Панель администратора").
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница "Мои заказы"**

Не доступна без авторизации!
Здесь расположены:
+ Список билетов, заказанных данным пользователем, по нажатию кнопки можно перейти на страницу информации о заказе (уже без кнопки подтверждения).
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница "Мои данные"**

Не доступна без авторизации!
Здесь расположены:
+ Данные пользователя: логин, ФИО, контактные данные (телефон, почта), есть возможность отредактировать эти данные и задать новый пароль.
+ Кнопка удаления пользователя.
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница "Панель администратора"**

Не доступна не админам!
Здесь расположены:
+ Кнопка "Новый рейс", по ней админ переходит на страницу добавления нового рейса.
+ Кнопка "Изменить или удалить рейс" с полем для номера рейса, по ней админ переходит на страницу изменения рейса.
+ Кнопка "Изменить или удалить пользователя" с полем для логина или id пользователя, по ней админ переходит на страницу "Мои данные" конкретного пользователя.
+ Кнопка "История покупок пользователя" с полем для логина или id пользователя, по ней админ переходит на страницу "Мои заказы" конкретного пользователя.
+ Кнопка "Список пассажиров" c полями для ввода названия компании перевозчика, номера рейса, точки А и точки Б, времени отправления и прибытия, по ней админ переходит на страницу списка пассажиров.
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница добавления нового рейса**

Не доступна не админам!
Здесь расположены:
+ Поле добавления новой остановки (с указанием времени отправления и прибытия, называния остановки), кнопка "Добавить в конец".
+ Список остановок с их данными (время отправления и прибытия, называние остановки), кнопки "Изменить" (открывает доступ к полям изменения данных) и "Удалить".
+ Поле указания номера маршрута и названия компании перевозчика.
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница изменения рейса**

Не доступна не админам!
Здесь расположены:
+ Поле добавления новой остановки (с указанием времени отправления и прибытия, называния остановки), кнопка "Добавить в конец".
+ Список остановок с их данными (время отправления и прибытия, называние остановки), кнопки "Изменить" (открывает доступ к полям изменения данных) и "Удалить".
+ Поле изменения номера маршрута и названия компании перевозчика, кнопка "Изменить".
+ Кнопка "Удалить рейс"
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).

**Страница списка пассажиров**

Не доступна не админам!
Здесь расположены:
+ Список пассажиров рейсов с заданным фильтром (указываются ФИО, логин, контактные данные, номер рейса, точки отправления и прибытия, времена отправления и прибытия, места).
+ Кнопка назад и домой (возврат на предыдущую и на стартовую соответственно).


# Сценарии использования:

**Без авторизации:**
+ Пользователь вводит пункт отправления и пункт назначения (из заданного списка), удобный диапазон дат и времен, и получает список доступных рейсов, наличие свободных мест и цены за эти места.
+ Пользователь может узнать подробную информацию о рейсе по нажатию кнопки рядом с одним из элементов списка (все остановки, во сколько туда прибывает и отправляется автобус).

**С авторизацией:**

+ Все то же самое, но появляется возможность забронировать билет на определенное место выбранного рейса (по нажатию кнопки рядом с выбранным местом).
+ Пользователь нажимает "Мои заказы" и получает доступ к истории своих заказов, прошедших и предстоящих.
+ Пользователь нажимает "Мои данные" и может изменить/дополнить информацию о себе или удалить аккаунт.

**Админы:**

+ Админ нажимает в панели администратора "Новый рейс" и может добавить новый рейс, введя его название, перевозчика и последовательно добавляя станции от стартовой к конечной.
+ Админ нажимает в панели администратора "Изменить или удалить рейс" и похожим образом изменяет рейс, удаляя/добавляя станции, меняя название/перевозчика.
+ Админ нажимает в панели администратора "Изменить или удалить пользователя", вводит логин или айди пользователя, попадает в меню аналогичное странице "Мои данные", где может изменить/дополнить информацию пользователя или удалить аккаунт.
+ Админ нажимает в панели администратора "История покупок пользователя", вводит логин или айди пользователя и получает доступ к истории его заказов, прошедших и предстоящих.
+ Админ нажимает в панели администратора "Список пассажиров", вводит пункт отправления и пункт назначения (из заданного списка) или перевозчика или номер рейса, диапазон дат и времен, и получает список рейсов и всех пассажиров, купивших билет на данные рейсы.

