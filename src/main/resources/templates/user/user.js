$(document).ready(function() {
    $('#join-form').on('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출을 막음

        // 입력값 가져오기
        var username = $('#username').val().trim();
        var email = $('#email').val().trim();
        var password = $('#password').val().trim();

        // 필수 필드 체크
        if (username === '' || email === '' || password === '') {
            alert('모든 필수 항목을 입력해주세요.');
            return;
        }

        // 회원가입 요청
        $.ajax({
            url: '/blog/join',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                username: username,
                email: email,
                password: password
            }),
            success: function(response) {
                alert(response); // 회원가입 성공 또는 실패 메시지를 alert로 표시
                if (response === "회원가입 성공") {
                    window.location.href = "/"; // 회원가입 성공 시 홈 페이지로 이동
                }
            },
            error: function(xhr, status, error) {
                alert('회원가입 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });
});
