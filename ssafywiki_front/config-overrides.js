const webpack = require('webpack');

module.exports = function override(config, env) {
    // 웹팩의 resolve.fallback 설정을 할당하거나 빈 객체를 사용
    const fallback = config.resolve.fallback || {};
    
    // 필요한 폴리필들을 추가
    Object.assign(fallback, {
        // 기존의 path-browserify 설정 유지
        path: require.resolve('path-browserify'),
        // fs 모듈은 브라우저에서 사용할 수 없으므로 false로 설정하여 무시
        fs: false,
        // process 폴리필 추가
        process: require.resolve('process'),
        // 기타 노드 코어 모듈 폴리필 추가 가능
    });
  
    // 수정된 fallback 객체를 원래의 config 객체에 할당
    config.resolve.fallback = fallback;

    // ProvidePlugin 설정을 추가하여 필요한 폴리필을 전역 변수로 제공
    config.plugins = (config.plugins || []).concat([
        new webpack.ProvidePlugin({
            process: 'process', // process를 process/browser로 매핑
            Buffer: ['buffer', 'Buffer'], // Buffer를 global.Buffer로 매핑
        }),
    ]);

    return config;
};