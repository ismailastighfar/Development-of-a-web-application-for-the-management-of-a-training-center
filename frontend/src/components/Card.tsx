import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleDollarToSlot, faClock, faLocationDot, faCalendar } from '@fortawesome/free-solid-svg-icons'


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
        <div className='card' onClick={cardData.SubmitOnClick}>
            <div className='card__title'>
                { cardData.title }
            </div>
            <div className="card__desc">{ cardData.category }</div>
            <div className='card__content'>
                <p className='card__content-front'>{ cardData.content }</p>

                <div className='card_external_data'>
                        <p><FontAwesomeIcon icon={faCircleDollarToSlot} />{ cardData.const } (DH)</p>
                        <p><FontAwesomeIcon icon={faClock} />{ cardData.hours }H</p>
                        <p><FontAwesomeIcon icon={faLocationDot} />{ cardData.city }</p>
                        <p><FontAwesomeIcon icon={faCalendar} />{ cardData.endEntrollData }</p>
                </div>

                {cardData.actions && (
                    <div className='card_actions'>
                        {cardData.actions}
                    </div>
                )}
            </div>
        </div>

    )
}

export default Card