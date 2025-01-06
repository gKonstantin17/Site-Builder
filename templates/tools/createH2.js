(function() {
    const id = crypto.randomUUID(); // Для выбора конкретного элемента
    var newP = document.createElement('h2');
    newP.id = id; // Присваиваем уникальный ID
    newP.textContent = 'Hello, I am a new h2!';
    if (selectedElementId) {
        const container = document.getElementById(selectedElementId);
        container.appendChild(newP);
    } else {
        const container = document.querySelector('.container');
        container.appendChild(newP);
    }
})();
