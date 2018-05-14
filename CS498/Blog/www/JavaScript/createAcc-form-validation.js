jQuery.validator.addMethod("checkPsw",
    function(value) {
        if (!/[A-Z]/.test(value)) {
            return false;
        } else if (!/[a-z]/.test(value)) {
            return false;
        } else if (!/[0-9]/.test(value)) {
            return false;
        }
        return true;
    }, "Password must contain an upper case, lower case, and number");

$().ready(function() {
    $("#createAccount").validate({
        errorPlacement: function (error, element) {
            element.after(error).after('<br/>');
        },
        rules: {
            uname: {
                required: true,
                minlength: 8,
                maxlength: 20
            },
            psw: {
                required: true,
                checkPsw: true,
                minlength: 6,
                maxlength: 40,
            },
            psw_match: {
                required: true,
                equalTo: "#psw"
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            uname: {
                required: "Please provide a username",
                minlength: "Your username must be at least 8 characters long",
                maxlength: "Your username must be at most 20 characters long",
            },
            psw: {
                required: "Please provide a password",
                minlength: "Your password must be at least 6 characters long",
                maxlength: "Your password must be at most 40 characters long",
            },
            psw_match: {
                required: "Please confirm your password",
                equalTo: "Passwords must match"
            },
        }
    });
});