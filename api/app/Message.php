<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Message extends Model
{
    protected $fillable = ['id','user_id','message','isRead'];

    public function user()
    {
    	return $this->belongsTo('App\User');
    }
}
