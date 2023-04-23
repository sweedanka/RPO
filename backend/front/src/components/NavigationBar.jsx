import React from 'react';
import {Navbar, Nav} from 'react-bootstrap'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faHome} from '@fortawesome/free-solid-svg-icons'
import {useNavigate} from 'react-router-dom';
import {Link} from 'react-router-dom'
import Utils from "../utils/Utils";
import BackendService from "../services/BackendService";
import {faUser} from '@fortawesome/free-solid-svg-icons'

class NavigationBarClass extends React.Component {

    constructor(props) {
        super(props);
        this.goHome = this.goHome.bind(this);
    }

    logout() {
        BackendService.logout().then(() => {
            Utils.removeUser();
            this.goHome()
        });
    }

    goHome() {
        this.props.navigate('Home');
    }


render() {
    let uname = Utils.getUserName();
    return (
        <Navbar bg="light" expand="lg">
            <Navbar.Brand><FontAwesomeIcon icon={faHome} />{' '}My RPO</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                    <Nav.Link as={Link} to="/home">Home</Nav.Link>
                    <Nav.Link onClick={this.goHome}>Another Home</Nav.Link>
                    <Nav.Link onClick={() =>{ this.props.navigate("\home")}}>Yet Another Home</Nav.Link>
                </Nav>
                <Navbar.Text>{uname}</Navbar.Text>
                { uname &&
                <Nav.Link onClick={this.logout}><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Выход</Nav.Link>
                }
                { !uname &&
                <Nav.Link as={Link} to="/login"><FontAwesomeIcon icon={faUser} fixedWidth />{' '}Вход</Nav.Link>
                }
            </Navbar.Collapse>
        </Navbar>
        );
    }
}

const NavigationBar = props => {
    const navigate = useNavigate()

    return <NavigationBarClass navigate={navigate} {...props} />
}

export default NavigationBar;
