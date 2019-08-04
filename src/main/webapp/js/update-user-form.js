$(document).ready(function () {
    $('#updateUserForm').validate({
        rules:{
            firstName:{
                required:true,
                minlength: 2,
                maxlength:20
            },
            street:{
                required:true
            },
            house:{
                required:true
            },
            apartment:{
                required:true
            },
            phone:{
                required:true,
                minlength:6,
                maxlength:20
            }
        }
    });
    $('#updatePasswordForm').validate({
        rules:{
            oldPassword:{
                required:true,
                minlength: 5,
                maxlength:20
            },
            newPassword:{
                required:true,
                minlength: 5,
                maxlength:20
            }
        }
    });
});