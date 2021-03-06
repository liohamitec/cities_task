[![Build Status](https://travis-ci.org/liohamitec/cities_task.svg?branch=master)](https://travis-ci.org/liohamitec/cities_task)

Тестовое задание для компании *****.
Методы минимально закомментированы. Алгоритм описан в данном файле.

В программе используются 2 основных класса: DataHandler и Greeter. Для них же сделаны тесты. Логгер пишет в текущую директорию в файл logging.

DataHandler принимает на вход массив аргументов командной строки и обрабатывает его, возвращая по запросу город и таймзону, созданные на основании входных данных. Алгоритм следующий: 
1. Если аргументов на входе больше одного, значит есть вероятность, что последним указан идентификатор таймзоны.
2. Проверяем последний аргумент. Если он не есть правильный идентификатор, то считаем, что он является частью названия города.
  *с учетом того, что существуют города из трех (а может даже и больше) слов, например Santiago de Chile, здесь возникает дилемма рассматривать последнее слово как часть города, или как неправильно написанную таймзону. Примера входных данных в техзадании не было, поэтому я выбрал первое.
3. Пытаемся из города получить часовой пояс. Городом считаем все входные данные, т.к. до этого убедились, что последний аргумент не является валидным идентификатором таймзоны.
4. Если не получается, то используем GMT для таймзоны и входные данные для имени города. На всех этапах проверяется, чтобы кроме пробелов и букв не было других символов в названии города.

5. Если аргумент на входе один, то считаем его городом и пытаемся найти ему таймзону, иначе таймзона = GMT.
6. Если аргументов 0 - ошибка.

Greeter принимает на вход город и таймзону. Определяет локаль и текущее время, далее берет обращение из файла с конкретной локалью.

Writer - интерфейс для вывода на случай, если нужно будет в дальнейшем реализовывать вывод не только в консоль.
