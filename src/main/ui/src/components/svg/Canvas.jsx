import React, { useRef, useEffect } from 'react';
import PropTypes from 'prop-types';

const Canvas = ({ height, width, draw }) => {
    const canvasRef = useRef(null);

    useEffect(() => {
        const context = canvasRef.current.getContext('2d');
        draw(context);
    });

    return <canvas ref={canvasRef} height={height} width={width} />;
};

Canvas.propTypes = {
    height: PropTypes.number.isRequired,
    width: PropTypes.number.isRequired,
    draw: PropTypes.func.isRequired,
};

export default Canvas;
