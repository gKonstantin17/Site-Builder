function timer(endDate, color, fontSize) {
    const id = crypto.randomUUID();
    var timerContent = document.createElement('div');
    timerContent.className = 'timer-content';
    timerContent.id = id; // Присваиваем уникальный ID
    timerContent.style.color = color;
    timerContent.style.fontSize = fontSize;

    var countdown = document.createElement('div');
    countdown.className = 'countdown';
    countdown.textContent = 'Загрузка...';
    countdown.setAttribute('value', endDate);

    timerContent.appendChild(countdown);

    if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(timerContent);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(timerContent);
    }
}
//timer('2025-01-01','black','32px');