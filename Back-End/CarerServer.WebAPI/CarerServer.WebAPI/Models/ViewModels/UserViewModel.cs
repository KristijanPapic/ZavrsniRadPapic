using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace CarerServer.WebAPI.Models.ViewModels
{
    public class UserViewModel
    {
        private Guid id;
        private string username;
        private string email;
        private string phoneNumber;
        private bool role;
        private string pictureURL;
        private int carerOverallRating;
        private int petHandlingRating;
        private int carerCourtesyRating;
        private int ownerOverallRating;
        private int petRating;
        private int ownerCourtesyRating;
        private string bio;
        private List<PetViewModel> pets;

        public Guid Id { get => id; set => id = value; }
        public string Username { get => username; set => username = value; }
        public string Email { get => email; set => email = value; }
        public string PhoneNumber { get => phoneNumber; set => phoneNumber = value; }
        public bool Role { get => role; set => role = value; }
        public string PictureURL { get => pictureURL; set => pictureURL = value; }
        public int CarerOverallRating { get => carerOverallRating; set => carerOverallRating = value; }
        public int PetHandlingRating { get => petHandlingRating; set => petHandlingRating = value; }
        public int CarerCourtesyRating { get => carerCourtesyRating; set => carerCourtesyRating = value; }
        public int OwnerOverallRating { get => ownerOverallRating; set => ownerOverallRating = value; }
        public int PetRating { get => petRating; set => petRating = value; }
        public int OwnerCourtesyRating { get => ownerCourtesyRating; set => ownerCourtesyRating = value; }
        public string Bio { get => bio; set => bio = value; }
        public List<PetViewModel> Pets { get => pets; set => pets = value; }
    }
}