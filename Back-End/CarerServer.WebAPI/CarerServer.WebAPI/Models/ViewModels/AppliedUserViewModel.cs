using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.Models.ViewModels
{
    public class AppliedUserViewModel
    {
        private Guid userId;
        private string userName;
        private string pictureURL;
        private int rating;

        public Guid UserId { get => userId; set => userId = value; }
        public string UserName { get => userName; set => userName = value; }
        public int Rating { get => rating; set => rating = value; }
        public string PictureURL { get => pictureURL; set => pictureURL = value; }
    }
}