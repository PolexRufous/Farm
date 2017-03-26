'use strict';
const webpack = require('webpack');
const dust = require('dustjs-linkedin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

const IS_DEVELOP_MODE = (typeof process.env.NODE_ENV == "string" && process.env.NODE_ENV == 'development');

module.exports = [{
    context: __dirname + '/src',
    entry: {
        buildmainpage: './mainpage/js/build.page',
        mainpage: './mainpage/js/main.page'
    },
    output: {
        path: path.resolve('../application/src/main/resources/static'),
        filename: 'mainpage/[name].js',
        library: '[name]'
    },
    module: {
        loaders: [
            {
                test: /\.dust$/,
                loader: 'dust-loader'
            },
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015', 'es2016']
                }
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.json$/,
                loader: 'json-loader'
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            dust: 'dustjs-linkedin'
        }),
        new HtmlWebpackPlugin({
            title: 'Farm Main',
            filename: 'mainpage/mainpage.html',
            template: 'mainpage/mainpage.ejs',
            chunks: ['buildmainpage', 'mainpage'],
        })
    ],

    watch: false,

    devtool: IS_DEVELOP_MODE ? "source-map" : false,
    cache: false

}];
