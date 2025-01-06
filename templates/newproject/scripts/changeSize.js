function changeSize(containerSelector) {
        const container = document.querySelector(containerSelector);
        if (!container) {
            console.error(`Container with selector "${containerSelector}" not found!`);
            return;
        }

        let isResizing = false;
        let currentResizer = null;
        let currentBlock = null;

        let startX, startY, startWidth, startHeight, startLeft, startTop;

        const onMouseMove = (e) => {
            if (!isResizing) return;

            const dx = e.clientX - startX;
            const dy = e.clientY - startY;

            // поведение блока при перемещении ручек
            if (currentResizer.classList.contains('right')) {
                currentBlock.style.width = `${startWidth + dx}px`;
            } else if (currentResizer.classList.contains('left')) {
                currentBlock.style.width = `${startWidth - dx}px`;
                currentBlock.style.left = `${startLeft + dx}px`;
            } else if (currentResizer.classList.contains('top')) {
                currentBlock.style.height = `${startHeight - dy}px`;
                currentBlock.style.top = `${startTop + dy}px`;
            } else if (currentResizer.classList.contains('bottom')) {
                currentBlock.style.height = `${startHeight + dy}px`;
            } else if (currentResizer.classList.contains('top-left')) {
                currentBlock.style.width = `${startWidth - dx}px`;
                currentBlock.style.height = `${startHeight - dy}px`;
                currentBlock.style.left = `${startLeft + dx}px`;
                currentBlock.style.top = `${startTop + dy}px`;
            } else if (currentResizer.classList.contains('top-right')) {
                currentBlock.style.width = `${startWidth + dx}px`;
                currentBlock.style.height = `${startHeight - dy}px`;
                currentBlock.style.top = `${startTop + dy}px`;
            } else if (currentResizer.classList.contains('bottom-left')) {
                currentBlock.style.width = `${startWidth - dx}px`;
                currentBlock.style.height = `${startHeight + dy}px`;
                currentBlock.style.left = `${startLeft + dx}px`;
            } else if (currentResizer.classList.contains('bottom-right')) {
                currentBlock.style.width = `${startWidth + dx}px`;
                currentBlock.style.height = `${startHeight + dy}px`;
            }
        };

        const onMouseUp = () => {
            isResizing = false;
            window.removeEventListener('mousemove', onMouseMove);
            window.removeEventListener('mouseup', onMouseUp);
        };

        const onMouseDown = (e, resizer) => {
            currentBlock = document.getElementById(resizer.dataset.id); // Получаем блок по ID

            isResizing = true;
            currentResizer = resizer;

            startX = e.clientX;
            startY = e.clientY;
            startWidth = parseInt(window.getComputedStyle(currentBlock).width, 10);
            startHeight = parseInt(window.getComputedStyle(currentBlock).height, 10);
            startLeft = parseInt(window.getComputedStyle(currentBlock).left, 10);
            startTop = parseInt(window.getComputedStyle(currentBlock).top, 10);

            window.addEventListener('mousemove', onMouseMove);
            window.addEventListener('mouseup', onMouseUp);
        };

        container.addEventListener('mousedown', (e) => {
            if (e.target.classList.contains('resizer')) {
                onMouseDown(e, e.target);
            }
        });
    }

    // Функция для перемещения элементов
    function moveElements(containerSelector) {
        const container = document.querySelector(containerSelector);
        if (!container) {
            console.error(`Container with selector "${containerSelector}" not found!`);
            return;
        }
    
        let isDragging = false;
        let offsetX, offsetY, currentElement;
    
        const onMouseMove = (e) => {
            if (!isDragging) return;
    
            // Вычисляем новое положение элемента
            currentElement.style.left = `${e.clientX - offsetX}px`;
            currentElement.style.top = `${e.clientY - offsetY}px`;
        };
    
        const onMouseUp = () => {
            // Завершаем перетаскивание
            isDragging = false;
            window.removeEventListener('mousemove', onMouseMove);
            window.removeEventListener('mouseup', onMouseUp);
        };
    
        const onMouseDown = (e) => {
            // Предотвращаем выделение текста
            e.preventDefault();
    
            // Проверяем, что клик был по элементу, который должен двигаться
            currentElement = e.target;
    
            // Убедитесь, что у элемента задано position: absolute или position: relative
            const rect = currentElement.getBoundingClientRect();
            
            // Сохраняем начальное положение мыши относительно элемента
            offsetX = e.clientX - rect.left;
            offsetY = e.clientY - rect.top;
    
            // Инициализируем флаг перетаскивания
            isDragging = true;
    
            // Добавляем обработчики для перемещения и завершения перетаскивания
            window.addEventListener('mousemove', onMouseMove);
            window.addEventListener('mouseup', onMouseUp);
        };
    
        container.addEventListener('mousedown', (e) => {
            if (e.target && e.target.id) { // Проверяем, что это элемент с id
                onMouseDown(e);
            }
        });
    }
    

function initChangeSize() {
    changeSize('body');
    moveElements('body');
}

