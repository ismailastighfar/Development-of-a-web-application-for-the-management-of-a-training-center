
export interface CardData {
    id: number;
    title: string;
    content: string;
    const : string;
    hours : string;
    category : string;
    city : string;
    endEntrollData : string;
    actions?: any;
    SubmitOnClick?: any;
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
                    <div className='card__external_line'>
                        <p>Cost : { cardData.const } (DH)</p>
                        <p>Duratoin : { cardData.hours } Hour</p>
                    </div>
                    <div className='card__external_line'>
                        <p>Category : { cardData.category }</p>
                        <p>City : { cardData.city }</p>
                    </div>
                    <div className='card__external_line'>
                        <p>End Entroll Data : { cardData.endEntrollData }</p>
                    </div>
                </div>
                <div className='card__button'>
                 {cardData.actions ? cardData.actions : 
                     <button className='btn btn-primary' onClick={cardData.SubmitOnClick}>Enroll</button>
                 }
            </div>
            </div>
        </div>

    )
}

export default Card