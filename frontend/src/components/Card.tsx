
export interface CardData {
    id: number;
    title: string;
    content: string;
    const : string;
    hours : string;
    SubmitOnClick : any;
}

export const Card = (cardData:CardData) => {
    return (
        <div className='card'>
            <div className='card__title'>
                { cardData.title }
            </div>
            <div className='card__content'>
                <p className='card__content-front'>{ cardData.content }</p>
                <div className='card_external_data'>
                    <p>{ cardData.const } (DH)</p>
                    <p>{ cardData.hours } Hour</p>
                </div>
                <div className='card__button'>
                <button className='btn btn-primary' onClick={cardData.SubmitOnClick}>Enroll</button>
            </div>
            </div>
        </div>

    )
}

export default Card