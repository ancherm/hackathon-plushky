import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import "./css/UserFormPage.css";
function UserFormDoc(props) {
    const [formData, setFormData] = useState({
        name: '',
        lastName: '',
        patronymic: '',
        group: '',
        quantity: 0,
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const form = e.target;
        const data = new FormData(form);

        fetch('/your-server-endpoint', {
            method: 'POST',
            body: data
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Ошибка сети');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
    };

    return (
        <div className={"form-container"}>

            <h1>Форма для офромления справки</h1>
            <form onSubmit={handleSubmit}>
                <div className={"input-container"}>
                    <label>Фамилия:</label>
                    <input
                        type="text"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                    />
                </div>
                <div className={"input-container"}>
                    <label>Имя:</label>
                    <input
                        type="text"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                    />
                </div>
                <div className={"input-container"}>
                    <label>Отчество:</label>
                    <input
                        type="text"
                        name="patronymic"
                        value={formData.patronymic}
                        onChange={handleChange}
                    />
                </div>
                <div className={"input-container"}>
                    <label>Группа:</label>
                    <input
                        type="text"
                        name="group"
                        value={formData.group}
                        onChange={handleChange}
                    />
                </div>
                <div className={"input-container"}>
                    <label>Количество экземпляров:</label>
                    <input
                        type="number"
                        name="quantity"
                        value={formData.quantity}
                        onChange={handleChange}
                        min={1}
                    />
                </div>
                <button type="submit" className={"button-form"}>Отправить</button>
            </form>
        </div>
    );
}

export default UserFormDoc;
