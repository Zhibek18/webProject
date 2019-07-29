$(document).ready(function () {
    $('#signupForm').validate({
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
            },
            first_name:{
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
                maxlength:12
            }
        }
    });
});