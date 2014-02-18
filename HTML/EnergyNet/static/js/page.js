$(document).ready(function() {
    var changePage, updateLogin;
    var isIngelogd = false;

    $("#menu").load('parts/menu.html');
    $("#right-sidebar").load('parts/right-sidebar.html');

    $('#page').bind('click', 'a', function(e) {
        var page = $(e.target).attr('href');
        if(page == undefined) { // Check if clicked element has a value in href attribute
            return;
        }

        page = page.replace('#', '');

        $("#menu a").removeClass('active');
        $(this).addClass('active');

        if(page == 'uitloggen') {
            window.location = window.location.href.replace(window.location.hash, '');
            return;
        }

        $("#content").load('pages/'+page+'.html');
    });

    $('#page').bind('submit', 'form', function(e) {
        e.preventDefault();

        var form = e.target;
        var id   = $(form).attr('id');

        if(id == 'login') {
            var password = $(form).find('input[type=password]').val();
            if(password != '') {
                $('#content').load('forms/login-success.html');
                isIngelogd = true;
                updateLogin();
            } else {
                $('#content').load('forms/login-failed.html');
            }
        } else if(id == 'registratie') {
            $('#content').load('forms/registratie.html');
        } else if(id == 'vraag-mailen') {
            $('#content').load('forms/vraag-mailen.html');
        } else if(id == 'vraag-chatten') {
            $('#content').load('forms/vraag-chatten.html');
        } else if(id == 'meterstanden') {
            $('#content').load('forms/meterstanden.html');
        }
    });

    updateLogin = function() {
        if(isIngelogd) {
            $("#ingelogd").html("<p>Welkom mevrouw, meneer<br>U bent ingelogd</p><a href=\"#uitloggen\">uitloggen</a>");
        }
    };
});

