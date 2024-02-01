import React from 'react';
import {Card  , CardData }from '../../components/Card';

const cardsData : CardData = {
    id: 1,
    title: "React",
    content: "React is a JavaScript library for building user interfaces. It is maintained by Facebook and a community of individual developers and companies.",
    const : "1000",
    hours : "20",
    SubmitOnClick : () => {alert("Hello")}
}

const Formations = () => {
    return (
        <div>
            <h1>Formations</h1>
            <Card {...cardsData} />
        </div>
    );
};

export default Formations;
