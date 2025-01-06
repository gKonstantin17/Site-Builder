(function() {
    
        const id = crypto.randomUUID(); // Для выбора конкретного элемента
    
        var newDiv = document.createElement('div');
        newDiv.id = id; // Присваиваем уникальный ID
        newDiv.style.border = '2px solid black';
        newDiv.style.padding = '10px';
        newDiv.style.width = '200px';
        newDiv.style.height = '150px';
        newDiv.style.position = 'absolute'; // Для правильного позиционирования ручек
        newDiv.style.overflow = 'hidden';
        newDiv.textContent = '';
        newDiv.style.zIndex = '5';
        
        // Добавляем "ручки" для изменения размера
        const resizers = [
            'top', 'bottom', 'left', 'right', // Стороны
            'top-left', 'top-right', 'bottom-left', 'bottom-right' // Углы
        ];

        resizers.forEach(resizer => {
            var resizerDiv = document.createElement('div');
            resizerDiv.classList.add('resizer', resizer);
            resizerDiv.dataset.id = id; // Привязываем ID к каждой "ручке"
            newDiv.appendChild(resizerDiv);
        });

        if (selectedElementId) {
            const container = document.getElementById(selectedElementId);
            container.appendChild(newDiv);
        } else {
            const container = document.querySelector('.container');
            container.appendChild(newDiv);
        }
})();
