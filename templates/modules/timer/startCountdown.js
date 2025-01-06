function startCountdown() {
    const countdown = document.querySelector('.countdown');
    if (!countdown) {
        console.error('Элемент с классом ".countdown" не найден.');
        return;
    }
    const endDate = countdown.getAttribute('value');
    const targetDate = new Date(endDate);
    
    if (isNaN(targetDate)) {
        countdown.textContent = 'Неверная дата';
        return;
    }
    function updateCountdown() {
        var now = new Date();
        var timeRemaining = targetDate - now;

        if (isNaN(timeRemaining)) {
            countdown.textContent = 'Неверная дата';
            return;
        }

        var days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));
        var hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

        countdown.textContent = `${days} дней ${hours} часов ${minutes} минут ${seconds} секунд`;

        if (timeRemaining <= 0) {
            countdown.textContent = 'Событие наступило!';
            clearInterval(intervalId); 
        }
    }

    var intervalId = setInterval(updateCountdown, 1000);
    updateCountdown();
}
startCountdown();