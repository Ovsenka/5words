
$('#senderBtn').on('click', function(event) {
	event.preventDefault();
	document.querySelector('#senderBtn').style.color = 'white';
    document.querySelector('#senderBtn').style.background = 'green';
    document.querySelector('#senderBtn').innerHTML = 'Отправляем рассылку...';
	$(this).closest('form').submit();
});
