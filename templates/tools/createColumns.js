function CreateColums(number) {

    
    // Создаем блок с колонками
    const columns = document.createElement('div');
    columns.classList.add('columns');
    
    // Задаем стили для контейнера колонок
    columns.style.display = 'flex';
    columns.style.gap = '20px';
    
    // Создаем 3 колонки и добавляем стили
    for (let i = 1; i <= Number(number); i++) {
      const column = document.createElement('div');
      column.classList.add('column');
      column.textContent = `Колонка ${i}`;
      const id = crypto.randomUUID();
      column.id = id;
      // Задаем стили для каждой колонки
      column.style.flex = '1';
      column.style.padding = '20px';
      column.style.backgroundColor = 'lightgray';
      column.style.border = '1px solid #ccc';
      
      columns.appendChild(column);
    }
    
    if (selectedElementId) {
      const container = document.getElementById(selectedElementId);
      container.appendChild(columns);
  } else {
      const container = document.querySelector('.container');
      container.appendChild(columns);
  }
  };
  // CreateColums(4);
  // или
  // CreateColums('4');