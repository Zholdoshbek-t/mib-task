package kg.tilek.mib.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SystemCode {

    SUCCESS(200, "Успешно"),
    USER_NOT_FOUND(1, "Пользователь не найден"),
    PARAMS_NOT_FOUND(1, "При регистрации необоходимо указать минимум номер телефона или почту"),
    EMAIL_NOT_FOUND(1, "У пользователя должна быть установлена минимум почта или номер телефона. " +
            "Невозможно удалить номер телефона по причине отсутствия почты."),
    PHONE_NUMBER_NOT_FOUND(1, "У пользователя должна быть установлена минимум почта или номер телефона. " +
            "Невозможно удалить почту по причине отсутствия номера телефона."),
    ENTRY_SUM_ZERO(1, "При регистрации начальная сумма должна превышать 0"),
    USERNAME_EXISTS(2, "Пользователь с переданным логином уже существует"),
    EMAIL_EXISTS(2, "Пользователь с переданной почтой уже существует"),
    PHONE_NUMBER_EXISTS(2, "Пользователь с переданным номером телефона уже существует"),
    JWT_EXCEPTION(3, "JWT не актуален или не начинается с Bearer"),
    TOKEN_IS_EXPIRED(4, "Время JWT просрочено"),
    INCORRECT_PASSWORD(4, "Неправильный пароль"),
    NOT_ENOUGH_MONEY(5, "Недостаточно средств для перевода"),
    INTERNAL_SERVER_ERROR(999, "Внутренняя ошибка сервера");

    private final Integer code;

    private final String message;
}
