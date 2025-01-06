(function() {
    // Генерируем уникальный ID для новой секции
    const id = crypto.randomUUID();

    // Создаем новый элемент <section>
    const newSection = document.createElement('section');
    newSection.id = id; // Присваиваем уникальный ID

    // Создаем вложенный <div class="container">
    const containerDiv = document.createElement('div');
    containerDiv.className = 'container';

    // Вкладываем <div> внутрь <section>
    newSection.appendChild(containerDiv);

    // Добавляем новую <section> в конец <body> или другого контейнера
    document.body.appendChild(newSection);
})();