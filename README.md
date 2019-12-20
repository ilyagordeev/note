# Simple Note
Тестовое задание от NAUMEN

Implemented on Spring Boot.

To see the result:
http://ec2-54-71-193-232.us-west-2.compute.amazonaws.com/


### Simple Note API

###### Добавить пользователя:
~~~~
url: /api
Method: POST
Body json data: {"type: "adduser", "name": somename, "password": somepass}
Ответ json: {"result":"ok", "value": "User $name added with hash ${password.sha256()}"}
            {"result":"error", "value": error code}
~~~~
###### Получить одну запись:
~~~~
url: /api
Method: POST
data: {"type: "note", "id": (int)}
Ответ json: {"result":"ok","heading": $text,"text": $text,"id": $int,"timestamp": $timestamp}
			{"result":"error", "value": error code}
~~~~

###### Получить список заголовков:
~~~~
url: /api
Method: POST
data: {"type: "contents", page: (int)}
Ответ json: {"result":"ok", "value": $html}
~~~~

###### Удалить запись:
~~~~
url: /api
Method: POST
data: {"type: "delete", "id": (int)}
Ответ json: {"result":"ok", "value": 0 если такой записи нет и 1 если удалена успешно}
			{"result":"error", "value": error code}
~~~~

###### Добавить запись:
~~~~
url: /api
Method: POST
data: {"type: "addnote", "heading": (text), "note": (text)}
Ответ json: {"result":"ok", "value": "Note added"}
			{"result":"error", "value": error code}
~~~~

###### Изменить запись:
~~~~
url: /api
Method: POST
data: {"type: "edit", "heading": (text), "note": (text), "id": (int)}
Ответ json: {"result":"ok", "value": "Note changed"}
			{"result":"error", "value": error code}
~~~~

###### Поиск по тексту:
~~~~
url: /api
Method: POST
data: {"type: "search", "text": (text)}
Ответ json: {"result":"ok", "value": $html}
			{"result": "nothing", "value": "Ничего не найдено"}
~~~~

###### Поиск по заголовку:
~~~~
url: /api
Method: POST
data: {"type: "search_head", "text": (text)}
Ответ json: {"result":"ok", "value": $html}
			{"result": "nothing", "value": "Ничего не найдено"}
~~~~