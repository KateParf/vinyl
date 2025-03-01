# Модель БД

### Логическая схема базы данных
![image](https://github.com/user-attachments/assets/69161349-1297-4a88-b5ec-95b453679823)

**Record ( Пластинка (компакт-диск) - общая информация ):**
* *ID*
* *Название*: varchar
* *Исполнители*: доп таблица связи м-м 
* *Год выпуска*: number
* *Издатель*: varchar
* *Жанр*: FK на жанр
* *Штрих-код или qr*: varchar

**Performer_record ( Исполнители-пластинки ):**
* *FK исполнителя*
* *FK пластинки*

**Cover ( Обложки пластинок ):**
* *ID*
* *FK на пластинку*
* *Ссылка на фото*: varchar

**Personal_record ( Пластинка - персональная информация ):**
* *ID*
* *FK на пластинку (общую)*
* *Пользователь*: FK на пользователя
* *Состояние*: number (enum)
* *Примечания*: varchar

**Genre ( Жанр ):**
* *ID*
* *Название*: varchar

**Performer ( Исполнитель ):**
* *ID*
* *Имя*: varchar
* *Признак (исполнитель/группа)*: number (enum)
* *Ссылка на фото*: varchar

**Исполнитель-группа:**
* *FK на исполнителя*
* *FK на исполнителя (группу)*

**Треки:**
* *ID*
* *Название*: varchar
* *Пластинка*: FK на пластинку (общую)

**Пользователь:**
* *ID*
* *Логин*: varchar
* *Пароль (хэш)*: varchar
* *Почта*: varchar
