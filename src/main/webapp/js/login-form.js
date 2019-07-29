$(document).ready(function () {
    $('#loginForm').validate({
        rules:{
            login:{
                required:true,
                minlength: 5,
                maxlength:20
            },
            password:{
                required:true,
                minlength: 5,
                maxlength:20
            }
        }
    });
});