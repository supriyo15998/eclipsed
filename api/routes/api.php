<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware(['auth:api'])->group(function () {
    Route::get('/profile', 'Api\AuthController@profile');
    Route::get('/my-messages', 'Api\MessageController@getmessages');
    Route::post('/logout', 'Api\AuthController@logout');
});
Route::post('/send-message', 'Api\MessageController@sendmessage');
Route::post('/search-user', 'Api\MessageController@search');
Route::post('/register', 'Api\AuthController@register');
Route::post('/login', 'Api\AuthController@login');