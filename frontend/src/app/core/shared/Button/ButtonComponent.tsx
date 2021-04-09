import React from 'react';
import './App.css';
import {StyledButton} from './ButtonStyles';

//TODO: use own UI components instead of ANT
const Button = (props: {
    primary: boolean,
    onClick: any,
    label: string
}) => {
    return (
        <StyledButton {...props} onClick={props.onClick}>
            {props.label}
        </StyledButton>
    );
};

export default Button;
