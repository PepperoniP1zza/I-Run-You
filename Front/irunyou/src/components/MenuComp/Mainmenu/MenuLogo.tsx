import "./menu.css";
import LogoImg from '../../../assets/logo2.png';

export default function MenuLogo() {
    return (
        <div className="menu-logo">
            <img className="logo-img" src={ LogoImg } alt="" />
            <div className="logo-txt" >I Run You</div>
            <i className="fa-solid fa-bars-staggered"></i>
        </div>
    )
}
