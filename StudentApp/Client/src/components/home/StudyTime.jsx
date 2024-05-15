const Schedule = () => {
    return (
        <div className="schedule">
            <h2>Расписание учебы</h2>
            <table>
                <thead>
                <tr>
                    <th>День недели</th>
                    <th>Предмет</th>
                    <th>Время</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Понедельник</td>
                    <td>Математика</td>
                    <td>9:00 - 10:30</td>
                </tr>
                <tr>
                    <td>Вторник</td>
                    <td>Физика</td>
                    <td>11:00 - 12:30</td>
                </tr>
                <tr>
                    <td>Среда</td>
                    <td>История</td>
                    <td>10:00 - 11:30</td>
                </tr>
                <tr>
                    <td>Четверг</td>
                    <td>Литература</td>
                    <td>9:00 - 10:30</td>
                </tr>
                <tr>
                    <td>Пятница</td>
                    <td>Английский язык</td>
                    <td>8:30 - 10:00</td>
                </tr>
                </tbody>
            </table>
        </div>
    );
}

// Использование компонента расписания
ReactDOM.render(<Schedule />, document.getElementById('root'));
