package hr.tvz.android.projectfranprizmic.Activity.Model

enum class UserValid(var code: Int) {
    VALID(0),
    USER_CREATED(1),
    NOTFOUND(2),
    EMPTY_USERNAME(3),
    EMPTY_PASSWORD(4),
    PASSWORD_MISMATCH(5),
    USERNAME_EXISTS(6),
    NETWORK_ERROR(10),
    ERROR(20);

    var user = null
}